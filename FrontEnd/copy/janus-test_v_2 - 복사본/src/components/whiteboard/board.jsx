import { Tldraw } from 'tldraw'
import './index.css'
import React from 'react'

export default function App() {
	return (
		<div style={{ position: 'fixed', inset: 0 }}>
			<Tldraw />
		</div>
	)
}