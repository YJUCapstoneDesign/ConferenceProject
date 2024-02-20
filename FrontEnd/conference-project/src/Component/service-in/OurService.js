function OurService() {
    return (
        <div>
            <section className="container mx-auto px-8 py-8 lg:py-40">
                <h2 className="block antialiased tracking-normal font-sans text-4xl font-semibold leading-[1.3] text-blue-gray-900 !text-3xl !leading-snug lg:!text-4xl">
                    Our Service
                </h2>
                <br />
                <p classNames="block antialiased font-sans text-xl font-normal leading-relaxed text-inherit mt-2 w-full font-nomal !text-gray-500 lg:w-5/12">
                    There are many variations of passages of Lorem Ipsum available, but the majority have suffered
                    alteration
                </p>
                <br />
                <div className="mt-10 grid grid-cols-1 gap-10 lg:grid-cols-3 text-left">
                    <div className="relative flex flex-col bg-clip-border rounded-xl bg-transparent text-gray-700 shadow-md relative grid min-h-[30rem] items-end overflow-hidden rounded-xl">
                        <img
                            src="./conference.jpg"
                            alt="bg"
                            className="absolute inset-0 h-full w-full object-cover object-center"
                        />
                        <div className="absolute inset-0 bg-black/70"></div>
                        <div className="p-6 relative flex flex-col justify-end">
                            <h4 className="block antialiased tracking-normal font-sans text-2xl font-semibold leading-snug text-white">
                                Video Conference
                            </h4>
                            <p className="block antialiased font-sans text-base font-light leading-relaxed text-white my-2 font-normal">
                                Start a video conference now
                            </p>
                        </div>
                    </div>
                    <div className="relative flex flex-col bg-clip-border rounded-xl bg-transparent text-gray-700 shadow-md relative grid min-h-[30rem] items-end overflow-hidden rounded-xl">
                        <img src="" alt="bg" className="absolute inset-0 h-full w-full object-cover object-center" />
                        <div className="absolute inset-0 bg-black/70"></div>
                        <div className="p-6 relative flex flex-col justify-end">
                            <h4 className="block antialiased tracking-normal font-sans text-2xl font-semibold leading-snug text-white">
                                Brain Storming
                            </h4>
                            <p className="block antialiased font-sans text-base font-light leading-relaxed text-white my-2 font-normal">
                                Use brainstorming techniques to generate ideas faster
                            </p>
                        </div>
                    </div>
                    <div className="relative flex flex-col bg-clip-border rounded-xl bg-transparent text-gray-700 shadow-md relative grid min-h-[30rem] items-end overflow-hidden rounded-xl">
                        <img src="" alt="bg" className="absolute inset-0 h-full w-full object-cover object-center" />
                        <div className="absolute inset-0 bg-black/70"></div>
                        <div className="p-6 relative flex flex-col justify-end">
                            <h4 className="block antialiased tracking-normal font-sans text-2xl font-semibold leading-snug text-white">
                                Summarize using AI
                            </h4>
                            <p className="block antialiased font-sans text-base font-light leading-relaxed text-white my-2 font-normal">
                                AI-powered summaries make meetings more efficient.
                            </p>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    );
}

export default OurService;
