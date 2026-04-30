

const Contact = () => {
    return (
        <div className="bg-gray-900 min-h-screen text-white py-20 px-6">
            <div className="max-w-3xl mx-auto">
                <div className="text-center mb-16">
                    <h2 className="text-4xl font-serif tracking-tight mb-4">Contact Our Concierge</h2>
                    <p className="text-gray-500 tracking-wide">Inquiries regarding bespoke pieces or catalog assistance.</p>
                </div>

                <form className="space-y-12">
                    <div className="grid md:grid-cols-2 gap-12">
                        {/* Name Input */}
                        <div className="relative group">
                            <input 
                                type="text" 
                                className="w-full bg-transparent border-b border-gray-700 py-3 outline-none focus:border-amber-500 transition-colors peer"
                                placeholder=" "
                            />
                            <label className="absolute left-0 top-3 text-gray-500 pointer-events-none transition-all peer-focus:-top-4 peer-focus:text-xs peer-focus:text-amber-500 peer-[:not(:placeholder-shown)]:-top-4 peer-[:not(:placeholder-shown)]:text-xs">
                                FULL NAME
                            </label>
                        </div>

                        {/* Email Input */}
                        <div className="relative group">
                            <input 
                                type="email" 
                                className="w-full bg-transparent border-b border-gray-700 py-3 outline-none focus:border-amber-500 transition-colors peer"
                                placeholder=" "
                            />
                            <label className="absolute left-0 top-3 text-gray-500 pointer-events-none transition-all peer-focus:-top-4 peer-focus:text-xs peer-focus:text-amber-500 peer-[:not(:placeholder-shown)]:-top-4 peer-[:not(:placeholder-shown)]:text-xs">
                                EMAIL ADDRESS
                            </label>
                        </div>
                    </div>

                    {/* Message Area */}
                    <div className="relative group">
                        <textarea 
                            rows="4"
                            className="w-full bg-transparent border-b border-gray-700 py-3 outline-none focus:border-amber-500 transition-colors peer resize-none"
                            placeholder=" "
                        ></textarea>
                        <label className="absolute left-0 top-3 text-gray-500 pointer-events-none transition-all peer-focus:-top-4 peer-focus:text-xs peer-focus:text-amber-500 peer-[:not(:placeholder-shown)]:-top-4 peer-[:not(:placeholder-shown)]:text-xs">
                            HOW CAN WE ASSIST YOU?
                        </label>
                    </div>

                    {/* Submit Button */}
                    <div className="flex justify-center">
                        <button className="group relative px-12 py-4 bg-white text-black overflow-hidden transition-all duration-300 hover:bg-amber-500">
                            <span className="relative z-10 font-medium tracking-[0.2em] uppercase text-sm">
                                Send Message
                            </span>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default Contact;