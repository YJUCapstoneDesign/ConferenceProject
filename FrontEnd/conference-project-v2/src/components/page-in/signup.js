import React from 'react';


const Signup = () => {
    return (
        <div className='Sign-in'>
            <div className="py-24 animated-background h-screen bg-gradient-to-r from-blue-500 via-blue-500 to-indigo-500">
              <div className="flex bg-white rounded-lg shadow-lg overflow-hidden mx-auto max-w-sm lg:max-w-4xl mb-5">
                    <div className="hidden lg:block lg:w-1/2 bg-cover" style={{backgroundImage: "url('https://source.unsplash.com/random/?conference')"}}></div>
                    <div className="w-full p-8 lg:w-1/2">
                        <a href="/"><p className='text-xs font-mono text-decoration-line: underline mb-5 inline-block'> Back to Website</p></a>
                        <h2 className="text-2xl font-bold text-gray-700 font-mono text-left">Signup!</h2>
                        {/* <p className="text-xl text-gray-600 text-center">Welcome back!</p> */}
                        <div className="mt-4">
                            <label className="block text-gray-700 text-sm font-bold font-mono mb-2  ">Name</label>
                            <input className="bg-custom-flesh text-gray-700 focus:outline-none focus:shadow-outline border border-custom-flesh rounded-3xl py-2 px-4 block w-full appearance-none" type="text" />
                        </div>
                        <div className="mt-4">
                            <label className="block text-gray-700 text-sm font-bold font-mono mb-2  ">Email</label>
                            <input className="bg-custom-flesh text-gray-700 focus:outline-none focus:shadow-outline border border-custom-flesh rounded-3xl py-2 px-4 block w-full appearance-none" type="email" />
                        </div>
                        <div className="mt-4">
                            <div className="flex justify-between">
                                <label className="block text-gray-700 text-sm  font-mono font-bold mb-2">Password</label>
                            </div>
                            <input className="bg-custom-flesh text-gray-700 focus:outline-none focus:shadow-outline border border-custom-flesh rounded-3xl py-2 px-4 block w-full appearance-none" type="password" />
                        </div>
                        <div className="mt-4">
                            <label className="block text-gray-700 text-sm font-bold font-mono mb-2  ">Address</label>
                            <input className="bg-custom-flesh text-gray-700 focus:outline-none focus:shadow-outline border border-custom-flesh rounded-3xl py-2 px-4 block w-full appearance-none" type="text" />
                        </div>
                        <div className="mt-4">
                            <label className="block text-gray-700 text-sm font-bold font-mono mb-2  ">Phone</label>
                            <input className="bg-custom-flesh text-gray-700 focus:outline-none focus:shadow-outline border border-custom-flesh rounded-3xl py-2 px-4 block w-full appearance-none" type="text" />
                        </div>
                        <div className="mt-10">
                            <button className="bg-gray-950 text-white font-bold py-2 px-4 w-full rounded-3xl hover:bg-gray-700 text-base">Sign up</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Signup;
