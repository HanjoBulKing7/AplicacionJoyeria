import { Swiper, SwiperSlide } from 'swiper/react'
import bannderData from '../../utils/bannderData';



const Banner = () => {

    return(
        <Swiper
            slidesPerView={1}
            
        >
            {bannderData.map((v, i)=>(
                <SwiperSlide>
                    <div className={`${v.backgroundColor} `}>
                        <h1>{v.title}</h1>
                    </div>
                </SwiperSlide>
            ))}
        </Swiper>
    );

}

export default Banner;