import {ClockLoader} from 'react-spinners';

const CustomSpinner = ({text}) => {

    return(
        <div className="flex flex-col items-center justify-center w-full pt-40 gap-7">
            <ClockLoader
            visible={true}
            height="96"
            width="96"
            color="grey"
            strokeWidth="5"
            animationDuration="0.75"
            ariaLabel="rotating-lines-loading"
            wrapperStyle={{}}
            wrapperClass=""
            />
            <p className='text-white text-3xl font-semibold'>
                { text ? text : "Plase wait" }
            </p>
        </div>
    );

}
export default CustomSpinner;