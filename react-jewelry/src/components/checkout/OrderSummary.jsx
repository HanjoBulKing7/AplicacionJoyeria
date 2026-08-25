import React from 'react'
import { FiPackage, FiMapPin, FiCreditCard, FiShield } from 'react-icons/fi'
import { SiStripe } from 'react-icons/si'
import { FaPaypal } from 'react-icons/fa'
import { formatPrice } from '../../../../../../../Escritorio/ecom-frontend/src/utils/formatPrice'
import { useCheckoutContext } from '../hooks/useCheckoutContext'
import { useSelector } from 'react-redux'


const SectionLabel = ({ icon: Icon, children }) => (
  <div className='flex items-center gap-2 mb-3'>
    <Icon size={13} className='text-zinc-500' />
    <span className='text-[10px] tracking-[0.12em] uppercase text-zinc-500 font-medium'>{children}</span>
  </div>
)


const OrderSummary = () => {

  const { cart } = useSelector((state)=> state.cart)
  const totalPrice = parseInt(cart?.reduce((acc, cur) => acc += (cur.quantity * cur.price), 0));
  const { checkoutAddress , checkoutMethod} = useCheckoutContext();

  const shipping = 0
  const grandTotal = (totalPrice || 0) + shipping

  return (
    <div className='max-w-lg mx-auto px-4 pb-32 pt-2'>

      <p className='text-center text-[10px] tracking-[0.2em] uppercase text-zinc-600 mb-6'>
        Review your order
      </p>

      {/* Items */}
      <div className='bg-zinc-900 border border-white/5 rounded-2xl p-5 mb-3'>

        <div className='space-y-0'>
          {cart?.map((item, i) => (
            <div
              key={item.productId ?? i}
              className='flex items-center gap-3 py-3 border-b border-white/5 last:border-0 last:pb-0 first:pt-0'
            >
              {/* Thumbnail */}
              <div className='w-12 h-12 rounded-lg bg-zinc-800 border border-white/5 flex items-center justify-center flex-shrink-0 overflow-hidden'>
                {item.image
                  ? <img src={item.image} alt={item.name} className='w-full h-full object-cover' />
                  : <span className='text-zinc-600 text-lg'>◇</span>
                }
              </div>

              {/* Info */}
              <div className='flex-1 min-w-0'>
                <p className='text-sm font-medium text-white truncate'>{item.name}</p>
                <div className='flex items-center gap-2 mt-0.5'>
                  <span className='text-xs text-zinc-500'>Qty {item.quantity}</span>
                  {item.availabilityStatus && (
                    <StatusBadge status={item.availabilityStatus} />
                  )}
                </div>
              </div>

              {/* Price */}
              <span className='text-sm font-medium text-white shrink-0'>
                {formatPrice((item.price ?? 0) * (item.quantity ?? 1))}
              </span>
            </div>
          ))}
        </div>

        <div className='space-y-1.5'>
          <div className='flex justify-between text-sm text-zinc-400'>
            <span>Subtotal</span>
            <span>{formatPrice(totalPrice ?? 0)}</span>
          </div>
          <div className='flex justify-between text-sm text-zinc-400'>
            <span>Shipping</span>
            <span className='text-emerald-400'>Free</span>
          </div>
        </div>

        <div className='border-t border-white/5 mt-4 pt-4 flex justify-between items-center'>
          <span className='text-sm font-medium text-white'>Total</span>
          <span className='text-xl font-semibold text-white'>{formatPrice(grandTotal)}</span>
        </div>
      </div>

      {checkoutAddress && (
        <div className='bg-zinc-900 border border-white/5 rounded-2xl p-5 mb-3'>
          <SectionLabel icon={FiMapPin}>Shipping to</SectionLabel>
          <div className='text-sm text-zinc-300 leading-relaxed'>
            <p className='font-medium text-white'>{checkoutAddress.name ?? checkoutAddress.street}</p>
            {checkoutAddress.street && checkoutAddress.name && (
              <p className='text-zinc-400 text-xs mt-0.5'>
                {[checkoutAddress.street, checkoutAddress.city, checkoutAddress.state, checkoutAddress.zipCode, checkoutAddress.country]
                  .filter(Boolean)
                  .join(', ')}
              </p>
            )}
          </div>
        </div>
      )}

      {checkoutMethod && (
        <div className='bg-zinc-900 border border-white/5 rounded-2xl p-5 mb-6'>
          <SectionLabel icon={FiCreditCard}>Payment</SectionLabel>
          <div className='flex items-center gap-3'>
            {checkoutMethod === 'Stripe'
              ? <SiStripe size={20} className='text-indigo-400' />
              : <FaPaypal size={20} className='text-blue-400' />
            }
            <span className='text-sm font-medium text-white'>{checkoutMethod}</span>
            <span className='ml-auto text-[10px] tracking-wide uppercase bg-zinc-800 text-zinc-400 border border-white/5 px-2 py-0.5 rounded'>
              Selected
            </span>
          </div>
        </div>
      )}

      <div className='flex items-center justify-center gap-1.5 mb-2'>
        <FiShield size={11} className='text-zinc-600' />
        <span className='text-[11px] text-zinc-600'>Secured and encrypted by Stripe</span>
      </div>

    </div>
  )
}

export default OrderSummary