import React from 'react';


function Sidebar() {
  return (
    <aside className="flex flex-col w-64 h-screen px-5 py-1 overflow-y-auto bg-white border-r rtl:border-r-0 rtl:border-l dark:bg-gray-900 dark:border-gray-700">
      <div className="flex flex-col justify-between flex-1 mt-6">
        <nav className="-mx-3 space-y-6">
          <div className="space-y-3">
            <a className="flex items-center px-3 py-2 text-gray-600 transition-colors duration-300 transform rounded-2xl dark:text-gray-200 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-gray-200 hover:text-gray-700" href="/">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style={{ fill: "rgba(0, 0, 0, 1)" }}>
                <path d="M3.299 17.596c.432 1.332 1.745 2.182 3.146 2.182H6.5A2.78 2.78 0 0 0 9.223 22c.457 0 .884-.115 1.262-.313a.992.992 0 0 0 .515-.882V3.027a.997.997 0 0 0-.785-.983 2.324 2.324 0 0 0-1.479.201c-.744.356-1.18 1.151-1.18 1.978v.055a2.778 2.778 0 0 0-2.744 4.433A3.327 3.327 0 0 0 2 12c0 1.178.611 2.211 1.533 2.812-.43.771-.571 1.746-.234 2.784zm15.889-8.885a2.778 2.778 0 0 0-2.744-4.433v-.055c0-.826-.437-1.622-1.181-1.978a2.32 2.32 0 0 0-1.478-.201.998.998 0 0 0-.785.983v17.777c0 .365.192.712.516.882.378.199.804.314 1.261.314a2.78 2.78 0 0 0 2.723-2.223h.056c1.4 0 2.714-.85 3.146-2.182.337-1.038.196-2.013-.234-2.784A3.35 3.35 0 0 0 22 12a3.327 3.327 0 0 0-2.812-3.289z"></path>
              </svg>
              <span className="mx-2 text-sm font-medium">Brain Storming</span>
            </a>

            <a className="flex items-center px-3 py-2 text-gray-600 transition-colors duration-300 transform rounded-2xl dark:text-gray-200 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-gray-200 hover:text-gray-700" href="/">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style={{ fill: "rgba(0, 0, 0, 1)" }}>
                <path d="M19 3c-1.654 0-3 1.346-3 3 0 .502.136.968.354 1.385l-1.116 1.302A3.976 3.976 0 0 0 13 8c-.739 0-1.425.216-2.02.566L9.566 7.152A3.449 3.449 0 0 0 10 5.5C10 3.57 8.43 2 6.5 2S3 3.57 3 5.5 4.57 9 6.5 9c.601 0 1.158-.166 1.652-.434L9.566 9.98A3.972 3.972 0 0 0 9 12c0 .997.38 1.899.985 2.601l-1.692 1.692.025.025A2.962 2.962 0 0 0 7 16c-1.654 0-3 1.346-3 3s1.346 3 3 3 3-1.346 3-3c0-.476-.121-.919-.318-1.318l.025.025 1.954-1.954c.421.15.867.247 1.339.247 2.206 0 4-1.794 4-4a3.96 3.96 0 0 0-.439-1.785l1.253-1.462c.364.158.764.247 1.186.247 1.654 0 3-1.346 3-3s-1.346-3-3-3zM7 20a1 1 0 1 1 0-2 1 1 0 0 1 0 2zM5 5.5C5 4.673 5.673 4 6.5 4S8 4.673 8 5.5 7.327 7 6.5 7 5 6.327 5 5.5zm8 8.5c-1.103 0-2-.897-2-2s.897-2 2-2 2 .897 2 2-.897 2-2 2zm6-7a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"></path>
              </svg>
              <span className="mx-2 text-sm font-medium">Mind Map</span>
            </a>

            <a className="flex items-center px-3 py-2 text-gray-600 transition-colors duration-300 transform rounded-2xl dark:text-gray-200 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-gray-200 hover:text-gray-700" href="/">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style={{ fill: "rgba(0, 0, 0, 1)" }}>
                <path d="M20 4H4c-1.103 0-2 .897-2 2v10c0 1.103.897 2 2 2h4l-1.8 2.4 1.6 1.2 2.7-3.6h3l2.7 3.6 1.6-1.2L16 18h4c1.103 0 2-.897 2-2V6c0-1.103-.897-2-2-2zM5 13h4v2H5v-2z"></path>
              </svg>
              <span className="mx-2 text-sm font-medium">White board</span>
            </a>
          </div>
        </nav>
      </div>
    </aside>
  );
}

export default Sidebar;
