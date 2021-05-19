import React from "react"
import {Link} from "gatsby"
import Container from "../components/container"
import Header from '../components/header'
import SubpageHeader from '../components/subpageheader'
import Footer from '../components/footer'
import MainWrapper from '../components/mainwrapper'
import * as dictionaryViewStyle from '../styles/dictionaryview.module.css'
import * as formStyle from '../styles/forms.module.css'


export default class DictionaryList extends React.Component {
    dictionary = {
            name: "Słownik medyczny",
            description: "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).",
            logoUrl: "https://euro-bion.pl/wp-content/uploads/2020/08/AdobeStock_120965550.jpeg"
        }

    state = {
        entryName: ""
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
				<SubpageHeader subpageName="Słownik"></SubpageHeader>
				<MainWrapper>
                    <div className={dictionaryViewStyle.row}>
                        <div className={dictionaryViewStyle.mainColumn}>
                            <div className={dictionaryViewStyle.dictNameRow}>
                                <div className={dictionaryViewStyle.category}>Nazwa:</div>
                                <div className={dictionaryViewStyle.dictName}>{this.dictionary.name}</div>
                            </div>
                            <div className={dictionaryViewStyle.row}>
                                <div className={dictionaryViewStyle.category}>Opis:</div>
                                <div className={dictionaryViewStyle.description}>{this.dictionary.description}</div>
                            </div>
                            <div className={dictionaryViewStyle.row}>
                                <form className={`form-horizontal ${formStyle.forms}`} onSubmit={this.handleSubmit}>
                                    <div className={`form-group ${formStyle.groupForms}`}>
                                        <label className={`control-label ${formStyle.labelForms}`}>Wyszukaj termin:</label>
                                        <input className={`form-control $formStyle.textInputForms}`}
                                            type="text" 
                                            name="entryName" 
                                            value={this.state.entryName} 
                                            onChange={this.handleInputChange}/>
                                    </div>
                                    <div className={`form-group ${formStyle.groupForms} ${formStyle.buttonGroupForm}`}>
                                        <button type="submit" className={`btn btn-primary ${formStyle.buttonSubmitForms}`}>Wyszukaj</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <img src={this.dictionary.logoUrl} alt="logo" width="250" height="250"></img>
                    </div>
				</MainWrapper>
				<Footer></Footer>
			</Container>
		)
  }
}