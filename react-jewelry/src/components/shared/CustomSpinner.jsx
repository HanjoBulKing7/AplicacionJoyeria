import {ClockLoader} from 'react-spinners';

const CustomSpinner = ({text}) => {

    return(
        <div className="flex flex-col items-center justify-center w-full pt-40 gap-7">
            <ClockLoader
                size={80}
                color="#ecff00"
                speedMultiplier={3}
            />
            <p className='text-white text-3xl font-semibold'>
                { text ? text : "Plase wait" }
            </p>
        </div>
    );

}
export default CustomSpinner;