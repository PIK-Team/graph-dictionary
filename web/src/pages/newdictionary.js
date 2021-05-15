import React from "react"
import Container from "../components/container"
import Header from '../components/header'
import SubpageHeader from '../components/subpageheader'
import Footer from '../components/footer'
import MainWrapper from '../components/mainwrapper'
import * as formStyle from '../styles/forms.module.css'

export default class NewDictionary extends React.Component {
	state = {
		dictionaryName: "",
		dictionaryDesc: "",
		dictionaryLogoUrl: ""
	}
	
	handleInputChange = event => {
		const target = event.target
		const value = target.value
		const name = target.name
		
		this.setState({
			[name]: value
		})
	}
	
	handleSubmit = event => {
		event.preventDefault()
		console.log("json: ", JSON.stringify(this.state))
	}
	
	render() {
		return(
			<Container>
				<Header></Header>
				<SubpageHeader subpageName="Dodawanie nowego słownika"></SubpageHeader>
				<MainWrapper>
					<form className={`form-horizontal ${formStyle.forms}`} onSubmit={this.handleSubmit}>
						<div className={`form-group ${formStyle.groupForms}`}>
							<label className={`control-label ${formStyle.labelForms}`}>Nazwa słownika:</label>
							<input type="text" className={`form-control $formStyle.textInputForms}`} name="dictionaryName" value={this.state.dictionaryName} onChange={this.handleInputChange}/>
						</div>
						<div className={`form-group ${formStyle.groupForms}`}>
							<label className={`control-label ${formStyle.labelForms}`}>Opis: </label>
							<textarea className={`form-control $formStyle.textInputForms}`} name="dictionaryDesc" value={this.state.dictionaryDesc} onChange={this.handleInputChange}> </textarea>
						</div>
						<div className={`form-group ${formStyle.groupForms}`}>
							<label className={`control-label ${formStyle.labelForms}`}> URL Logo:</label>
							<input className={`form-control $formStyle.textInputForms}`} value={this.state.dictionaryLogoUrl} onChange={this.handleInputChange} type="text" name="dictionaryLogoUrl" />
						</div>
						<div className={`form-group ${formStyle.groupForms} ${formStyle.buttonGroupForm}`}>
							<button type="submit" className={`btn btn-primary ${formStyle.buttonSubmitForms}`}>Dodaj</button>
						</div>
					</form>
				</MainWrapper>
				<Footer></Footer>
			</Container>
		)
  }
}