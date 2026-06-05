import Banner from './Banner'
import JewelryDish from '../../assets/home/JewelsDish.jpg'
import Chain from '../../assets/home/categories/chain.png'
import Bracelet from '../../assets/home/categories/bracelet.png'
import Earrings from '../../assets/home/categories/earrings.png'
import Ring from '../../assets/home/categories/ring.png'
import Rosary from '../../assets/home/categories/rosary.png'

const categoriesList = [
    {  
        id: 1,
        name: 'Chains',
        icon: Chain
    },
    {
        id: 2,
        name: 'Bracelets',
        icon: Bracelet
    },
    {
        id: 3,
        name: 'Earrings',
        icon: Earrings
    },
    {
        id: 4,
        name: 'Rings',
        icon: Ring
    },
    {
        id: 5,
        name: 'Rosaries',
        icon: Rosary
    },
]

const Home = () => {

    return(
        <div className='bg-black min-h-[calc(100vh-80px)] p-0'>
            <div className='border-4 border-amber-50/20 hover:border-white py-5 mx-20 items text-center'>
                <h1 className='text-white text-5xl font-montserrat tracking-tighter pt-10'>Find out all our products and the best prices ever</h1>
                <div className='sm:flex sm:flex-col sm:flex-col md:grid md:grid-cols-2 md:mt-10'>
                    <div className='md:flex md:flex-col items-center justify-center'>
                        <ul className='md:space-x-4 md:flex md:flex-row'>
                            {
                                categoriesList.map(c => {
                                    return(
                                    <li key={c.id} 
                                    className='group text-white border-2 border-amber-50/40 p-5 rounded-md hover:scale-110 hover:border-white shadow-xl hover:shadow-amber-50'>
                                        {c.name}
                                        <img src={c.icon} alt={`Icon of ${c.name} category`} className='h-15 w-15 group-hover:scale-110'/>
                                    </li>
                                    );
                                })
                            }
                        </ul>
                    </div>
                    <div>
                        <img src={JewelryDish} alt="Representative image of the tast for jewelry" className='h-70 w-120'/>
                    </div>
                </div>
            </div>
            <div className='flex items-center justify-center mt-20'>
                <span className='text-white text-5xl'>More content coming soon...</span>
            </div>

        </div>
    );
}

export default Home;