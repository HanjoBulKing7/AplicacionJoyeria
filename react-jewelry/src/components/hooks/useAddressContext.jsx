import { createContext, useContext, useState } from 'react';

const AddressModalContext = createContext();

export const AddressModalProvider = ({ children }) => {
  const [openModal, setOpenModal] = useState(false);
  const [ editingAddress, setEditingAddress ] = useState({})

  const openAddressFormModal = (editingAddress = null) => {
    setOpenModal(true);
    setEditingAddress(editingAddress);
  };

  const closeAddressFormModal = () => {
    setOpenModal(false);
    setEditingAddress(null);
  };

  return (
    <AddressModalContext.Provider value={{ openModal, setOpenModal, editingAddress , setEditingAddress , openAddressFormModal , closeAddressFormModal }}>
      {children}
    </AddressModalContext.Provider>
  );
};

export const useAddressModal = () => useContext(AddressModalContext);