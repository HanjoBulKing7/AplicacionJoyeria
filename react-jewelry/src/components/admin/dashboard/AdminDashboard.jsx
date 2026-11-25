import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { toast } from 'react-hot-toast';
import { fetchDashboardData } from '../../../redux/actions/authActions';

const money = new Intl.NumberFormat('es-MX', { style: 'currency', currency: 'MXN' });
const count = new Intl.NumberFormat('es-MX');

const StatCard = ({ label, value, accent = 'text-white', className = '' }) => (
  <div className={`border border-blue-500 rounded-xl p-6 shadow-lg flex flex-col gap-2 ${className}`}>
    <span className='text-xs uppercase tracking-widest text-black'>{label}</span>
    <span className={`text-4xl font-semibold ${accent}`}>{value}</span>
  </div>
);

const AdminDashboard = () => {
  const dispatch = useDispatch();
  const { dashboardData: d } = useSelector((state) => state.auth);

  useEffect(() => {
    dispatch(fetchDashboardData())
      .unwrap()
      .catch((e) => toast.error(typeof e === 'string' ? e : 'Error fetching dashboard data'));
  }, [dispatch]);

  const show = (v) => (v == null ? '—' : count.format(v));
  const updatedAt = d?.generatedAt ? new Date(d.generatedAt * 1000).toLocaleString('es-MX') : null;

  return (
    <div className='flex flex-col items-center w-full h-full p-8'>
      <h1 className='text-4xl text-blue-600 font-bold mb-10'>Dashboard</h1>

      <div className='grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-3 gap-6 w-full max-w-4xl'>
        <StatCard label='Inventory' value={show(d?.inventoryCount)} accent='text-black' />
        <StatCard label='Categories' value={show(d?.categoryCount)} accent='text-black' />
        <StatCard label='Active items' value={show(d?.activeQuantity)} accent='text-emerald-400' />
        <StatCard label='Inactive items' value={show(d?.inactiveQuantity)} accent='text-red-400' />
        <StatCard
          label='Total sold'
          value={d?.totalSold == null ? '—' : money.format(d.totalSold)}
          accent='text-amber-400'
          className='sm:col-span-2'
        />
      </div>

      {updatedAt && <p className='text-sm text-slate-500 mt-8'>Updated: {updatedAt}</p>}
    </div>
  );
};

export default AdminDashboard;