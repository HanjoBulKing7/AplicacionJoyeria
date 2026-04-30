import Banner from './Banner'

const Home = () => {

    return(
        <div className='bg-gray-900 h-screen'>
            <Banner />
            <div className='flex justify-center mt-5'>
                <h1 className='text-white text-5xl'>Find out all our products and the best prices ever</h1>
            </div>
        </div>
    );
}

export default Home;