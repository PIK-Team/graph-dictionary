import React from "react"
import Container from "../components/container"
import Header from '../components/header'
import SubpageHeader from '../components/subpageheader'
import Footer from '../components/footer'
import MainWrapper from '../components/mainwrapper'
import * as formStyle from '../styles/forms.module.css'


export default class NewEntry extends React.Component {

    state = {
        entryName: '',
        entryDefinition: '',
        entryParent: '',
    }

    handleInputChange = event => {
        const target = event.target
        const value = target.value
        const name = target.name

        this.setState({
            [name]: value,
        })
    }

    handleSubmit = event => {
        event.preventDefault()
		console.log("json: ", JSON.stringify(this.state))
    }

    render() {
        return (
            <Container>
                <Header></Header>
                <SubpageHeader subpageName="Dodawanie nowego wpisu"></SubpageHeader>
                <MainWrapper>
                    <form className={`form-horizontal ${formStyle.forms}`} onSubmit={this.handleSubmit}>
                        <div className={`form-group ${formStyle.groupForms}`}>
                        <label className={`control-label ${formStyle.labelForms}`}>Hasło:</label>
                            <input className={`form-control $formStyle.textInputForms}`} 
                                type="text"
                                name="entryName"
                                value={this.state.entryName}
                                onChange={this.handleInputChange}/>
                        </div>
                        <div className={`form-group ${formStyle.groupForms}`}>
							<label className={`control-label ${formStyle.labelForms}`}>Definicja: </label>
                            <textarea className={`form-control $formStyle.textInputForms}`} 
                                type="text"
                                name="entryDefinition"
                                value={this.state.entryDefinition}
                                onChange={this.handleInputChange}/>
                        </div>
                        <div className={`form-group ${formStyle.groupForms}`}>
							<label className={`control-label ${formStyle.labelForms}`}> Wpis nadrzędny:</label>
							<input className={`form-control $formStyle.textInputForms}`}
                                type="text"
                                name="entryParent"
                                value={this.state.entryParent}
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