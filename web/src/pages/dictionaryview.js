import React from "react"
import {navigate, Redirect} from "gatsby"
import Container from "../components/container"
import queryString from "query-string"
import Header from '../components/header'
import SubpageHeader from '../components/subpageheader'
import Footer from '../components/footer'
import MainWrapper from '../components/mainwrapper'
import * as dictionaryViewStyle from '../styles/dictionaryview.module.css'
import * as formStyle from '../styles/forms.module.css'


export default class DictionaryView extends React.Component {

    values = queryString.parse(this.props.location.search)
	
	dictionaryParam = this.values.dictionary

    state = {
        entryName: "",
        dictionary: null,
    }

    componentDidMount() {
		fetch(process.env.API_URL+'dictionaries/'+this.dictionaryParam+'/getByName', {
			method: 'GET',
		})
		.then(response => response.json())
		.then(json => {
            this.setState({dictionary:json});
        });
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
        navigate(`/entryview?dictionary=${this.state.dictionary[0].dictionaryName}&entry=${this.state.entryName}`)
	}
	
	render() {

        if (this.state.dictionary === null) {
            return(
                <Container>
                    <Header></Header>
                    <SubpageHeader subpageName="Słownik"></SubpageHeader>
                    <MainWrapper>
                            <center>ŁADOWANIE</center>
                    </MainWrapper>
                    <Footer></Footer>
                </Container>
            )
        }

		return(
			<Container>
				<Header></Header>
				<SubpageHeader subpageName="Słownik"></SubpageHeader>
				<MainWrapper>
                    <div className={dictionaryViewStyle.row}>
                        <div className={dictionaryViewStyle.mainColumn}>
                            <div className={dictionaryViewStyle.dictNameRow}>
                                <div className={dictionaryViewStyle.category}>Nazwa:</div>
                                <div className={dictionaryViewStyle.dictName}>{this.state.dictionary[0].dictionaryName}</div>
                            </div>
                            <div className={dictionaryViewStyle.row}>
                                <div className={dictionaryViewStyle.category}>Opis:</div>
                                <div className={dictionaryViewStyle.description}>{this.state.dictionary[0].description}</div>
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
                        <img src={this.state.dictionary[0].imageURI} alt="logo" width="250" height="250"></img>
                    </div>
				</MainWrapper>
				<Footer></Footer>
			</Container>
		)
  }
}