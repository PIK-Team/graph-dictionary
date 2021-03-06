import * as React from 'react'
import * as subpageHeaderStyle from '../styles/suppageheader_component.module.css'

const SubpageHeader = ({subpageName}) => {
	return (
		<div className={subpageHeaderStyle.wrapper}>
			<span className={subpageHeaderStyle.name}>
				{subpageName}
			</span>
		</div>
	)
}

export default SubpageHeader
