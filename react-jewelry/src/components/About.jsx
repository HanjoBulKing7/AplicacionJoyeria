const About = () => {
    return (
        <div className="bg-gray-900 min-h-screen text-white font-light">
            {/* Hero Section */}
            <div className="py-20 px-6 max-w-7xl mx-auto text-center">
                <h1 className="text-amber-400 text-sm tracking-[0.3em] uppercase mb-4">The New Standard</h1>
                <h2 className="text-5xl md:text-7xl font-serif mb-10 italic">Unveiling Excellence</h2>
                <div className="w-24 h-[1px] bg-amber-600 mx-auto mb-16"></div>
            </div>

            {/* Main Content */}
            <div className="max-w-5xl mx-auto px-6 pb-20 grid md:grid-cols-2 gap-16 items-center">
                <div className="space-y-6">
                    <h3 className="text-2xl text-amber-50">A Legacy in the Making</h3>
                    <p className="text-gray-400 leading-relaxed text-lg">
                        Though our journey is just beginning, our vision is timeless. We emerged to disrupt the 
                        traditional jewelry landscape, proving that <span className="text-white">uncompromising quality</span> 
                        doesn't have to be a privilege of the few.
                    </p>
                    <p className="text-gray-400 leading-relaxed">
                        Our catalog is a curated universe of diversity. From the minimalist strength of raw silver 
                        to the intricate brilliance of hand-set diamonds, we offer a vast collection that speaks 
                        every style language.
                    </p>
                </div>
                
                {/* Stats / Features Box */}
                <div className="border border-gray-800 p-10 bg-black/30 backdrop-blur-sm">
                    <div className="grid grid-cols-1 gap-8">
                        <div>
                            <span className="text-amber-500 text-3xl font-light">1000+</span>
                            <p className="text-xs uppercase tracking-widest text-gray-500 mt-2">Curated Pieces</p>
                        </div>
                        <div className="w-full h-[1px] bg-gray-800"></div>
                        <div>
                            <span className="text-amber-500 text-3xl font-light">Infinite</span>
                            <p className="text-xs uppercase tracking-widest text-gray-500 mt-2">Diversity & Styles</p>
                        </div>
                        <div className="w-full h-[1px] bg-gray-800"></div>
                        <div>
                            <span className="text-amber-500 text-3xl font-light">Fair</span>
                            <p className="text-xs uppercase tracking-widest text-gray-500 mt-2">Ethical Pricing</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};


export default About;