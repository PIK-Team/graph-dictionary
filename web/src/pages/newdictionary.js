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
		description: "",
		imageURI: ""
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
		if (this.state.dictionaryName != "")
		{
			fetch(process.env.API_URL+"/dictionaries", {
				method: "POST",
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify(this.state)
			});
		}
		
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
							<input className={`form-control $formStyle.textInputForms}`}
								type="text" 
								name="dictionaryName" 
								value={this.state.dictionaryName} 
								onChange={this.handleInputChange}/>
						</div>
						<div className={`form-group ${formStyle.groupForms}`}>
							<label className={`control-label ${formStyle.labelForms}`}>Opis: </label>
							<textarea className={`form-control $formStyle.textInputForms}`} 
								type="text"
								name="description" 
								value={this.state.description} 
								onChange={this.handleInputChange}/>
						</div>
						<div className={`form-group ${formStyle.groupForms}`}>
							<label className={`control-label ${formStyle.labelForms}`}> URL Logo:</label>
							<input className={`form-control $formStyle.textInputForms}`}
								type="text" 
								name="imageURI"
								value={this.state.imageURI} 
								onChange={this.handleInputChange}/>
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