import * as React from 'react'
import * as MainWrapperStyle from '../styles/mainwrapper_component.module.css'

const MainWrapper = ({children}) => {
	return (
		<div className={MainWrapperStyle.mainwrapper}>
			{children}
		</div>
	)
}

export default MainWrapper
