import { useEffect, useState } from 'react';

const IMAGES_URL = `${import.meta.env.VITE_BACKEND_URL}/api/public/images`; // ajusta a tu base
const ALLOWED = ['image/jpeg', 'image/png', 'image/webp'];
const MAX_SIZE = 5 * 1024 * 1024; // 5 MB

const ImageModal = ({ imageName, onUpload, loading = false }) => {
  const [file, setFile] = useState(null);
  const [preview, setPreview] = useState(null);
  const [error, setError] = useState('');

  // Preview local del archivo elegido, con limpieza de la URL temporal
  useEffect(() => {
    if (!file) { setPreview(null); return; }
    const url = URL.createObjectURL(file);
    setPreview(url);
    return () => URL.revokeObjectURL(url);
  }, [file]);

  const handleChange = (e) => {
    const selected = e.target.files?.[0];
    if (!selected) return;
    if (!ALLOWED.includes(selected.type)) return setError('Only JPG, PNG or WEBP images');
    if (selected.size > MAX_SIZE) return setError('Image must be under 5 MB');
    setError('');
    setFile(selected);
  };

  const shown = preview ?? (imageName ? `${import.meta.env.VITE_BACKEND_URL}/public/images/${imageName}` : null);

  return (
    <div className='flex flex-col items-center gap-6 w-full mt-10'>
      {shown ? (
        <img src={shown} alt='Item' className='max-h-80 max-w-full rounded-lg border object-contain' />
      ) : (
        <p className='text-gray-500 text-xl italic'>This item has no image yet</p>
      )}

      <label className='cursor-pointer bg-gray-200 hover:bg-gray-300 rounded-md px-4 py-2 text-lg'>
        {imageName || file ? 'Choose another image' : 'Choose an image'}
        <input type='file' accept='image/jpeg,image/png,image/webp' onChange={handleChange} className='hidden' />
      </label>

      {error && <p className='text-red-600 font-semibold'>{error}</p>}

      <button
        disabled={!file || loading}
        onClick={() => onUpload(file)}
        className='bg-blue-600 text-white text-xl rounded-md px-4 py-2 disabled:opacity-40 disabled:cursor-not-allowed'
      >
        {loading ? 'Uploading...' : 'Save image'}
      </button>
    </div>
  );
};

export default ImageModal;