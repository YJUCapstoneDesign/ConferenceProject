import { useEditor, Tldraw } from 'tldraw'
import 'tldraw/tldraw.css'
import React, { useEffect, useState } from 'react'

export default function Board() {
	const editor = useEditor()

	console.log(editor)
	
	return (
		<div style={{ position: 'fixed', inset: 0 }}>
			<Tldraw 
				editor={editor}
				onReady={() => {
					editor.setCanvas({
						background: '#fff',
						gridSize: 10,
						gridColor: '#ddd',
						gridOpacity: 0.5,
					})
				}}
			/>
		</div>
	)
}