import { Swiper, SwiperSlide } from 'swiper/react'
import { Navigation, Pagination } from 'swiper/modules'
import bannerData from '../../utils/bannerData'
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import 'swiper/css/effect-fade'
import 'swiper/css/autoplay'
import { Link } from 'react-router-dom';

const Banner = () => {

    return(
        <Swiper 
        modules={{Navigation, Pagination}}
        navigation
        pagination={{clickable: true}}
        loop
        grabCursor={true}
        autoplay={{
            delay: 4000,
            disableOnInteraction: false,
        }}
        >
            {bannerData.map((v, i)=>(
                <SwiperSlide key={i} >
                    <div className={`${v.backgroundColor} h-full`}>
                        <div className='flex flex-col space-y-15 items-center py-4 md:flex md:flex-row md:justify-center  md:space-x-20'>
                            <div className='flex flex-col'>
                                <h1>{v.title}</h1>
                                <p>{v.description}</p>
                            </div>
                            <div className='flex flex-col'>
                                <img  src={v.image} alt={v.title} />
                                <button className='bg-black text-amber-50 p-2'>
                                    <Link to='/items'>Shop</Link>
                                </button>
                            </div>
                        </div>
                    </div>
                </SwiperSlide>
            ))}
        </Swiper>
    );

}

export default Banner;