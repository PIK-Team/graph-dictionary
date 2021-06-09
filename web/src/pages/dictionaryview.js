import React from "react"
import {navigate, Link} from "gatsby"
import Container from "../components/container"
import queryString from "query-string"
import Header from '../components/header'
import SubpageHeader from '../components/subpageheader'
import Footer from '../components/footer'
import MainWrapper from '../components/mainwrapper'
import * as dictionaryViewStyle from '../styles/dictionaryview.module.css'
import * as formStyle from '../styles/forms.module.css'
import * as indexStyle from '../styles/index.module.css'
import * as newDicStyle from '../styles/newdic.module.css'


export default class DictionaryView extends React.Component {

    values = queryString.parse(this.props.location.search)
	
	dictionaryParam = this.values.dictionary

    state = {
        entryName: "",
        dictionary: null,
        hints: null
    }


    componentDidMount() {
		fetch(process.env.API_URL+'dictionaries/'+this.dictionaryParam+'/getByName', {
			method: 'GET',
		})
		.then(response => response.json())
		.then(json => {
            this.setState({dictionary:json});
        });

        window.addEventListener('click', event => {
            if (!event.target.closest('#autocompleteList'))
                this.setState({"hints": null})  
            },
            true);
	}	

    handleInputChange = event => {
		const target = event.target
		const value = target.value
		const name = target.name
        
        this.setState({
			[name]: value
		});

        if (value.length == 0) {
            this.state.hints = null;
            return;
        }

        fetch(process.env.API_URL+`entries/${this.state.dictionary[0].dictionaryName}/hints/${value}`, {
			method: 'GET',
		})
        .then(function(response) {
            if (response.ok)
                return response;
        })
        .then(response => response.json())
		.then(json => this.setState({hints:json}));

		
		
	}

    handleClick = event => {
        event.stopPropagation();
        const target = event.target
		const value = target.innerText
        

        this.setState({"entryName" : value, "hints": null});
    }
	
	handleSubmit = event => {
		event.preventDefault()

        const state = this.state;
        let ErrorFindingEntry = document.getElementById("ErrorFindingEntry");
		ErrorFindingEntry.style.display="none";

        fetch(process.env.API_URL+`entries/${this.state.dictionary[0].dictionaryName}/${this.state.entryName}/overview`, {
			method: 'GET',
		})
        .then(function(response) {
            if (!response.ok)
            {
                ErrorFindingEntry.style.display="block";
            }
            else
            {
                navigate(`/entryview?dictionary=${state.dictionary[0].dictionaryName}&entry=${state.entryName}`);

            }
        })

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
                    <div id="ErrorFindingEntry" className={ `${newDicStyle.responseStyle} ${newDicStyle.errorAddedDic}` }>Nie ma takiego wpisu w słowniku.</div>
                    <div className={dictionaryViewStyle.row}>
				
                        <div className={dictionaryViewStyle.mainColumn}>
						
							{ this.state.dictionary[0].imageURI != "" && this.state.dictionary[0].imageURI != null && <img src={this.state.dictionary[0].imageURI} alt="logo" width="250" height="250" className={dictionaryViewStyle.dictionaryLogo} ></img> }
							
                            <div className={dictionaryViewStyle.dictNameRow}>
                                <div className={dictionaryViewStyle.category}>Nazwa:</div>
                                <div className={dictionaryViewStyle.dictName}>{this.state.dictionary[0].dictionaryName}</div>
								
								
                            </div>
                            <div className={dictionaryViewStyle.row}>
                                <div className={dictionaryViewStyle.category}>Opis:</div>
                                <div className={dictionaryViewStyle.description}>{this.state.dictionary[0].description}</div>
                            </div>
                            <div className={dictionaryViewStyle.row}>
                                <form className={`form-horizontal ${formStyle.forms}`} onSubmit={this.handleSubmit} autocomplete="off">
                                    <div className={`form-group ${formStyle.groupForms}`}>
                                        <label className={`control-label ${formStyle.labelForms}`}>Wyszukaj termin:</label>
                                        <div className={formStyle.autocomplete}>
                                            <input className={`form-control $formStyle.textInputForms}`}
                                            id="entryInput"
                                            type="text" 
                                            name="entryName" 
                                            value={this.state.entryName} 
                                            onChange={this.handleInputChange}/>
                                            {this.state.hints != null &&
                                                <div id="autocompleteList" name="autocompleteList" className={formStyle.autocompleteItems}>
                                                    {this.state.hints.map(hint => (
                                                            <div onClick={this.handleClick}>
                                                                {hint}
                                                            </div>
                                                    ))}
                                                </div>
                                            }
                                        </div>
                                    </div>
                                    <div className={`form-group ${formStyle.groupForms} ${formStyle.buttonGroupForm}`}>
                                        <button type="submit" className={`btn btn-primary ${formStyle.buttonSubmitForms}`}>Wyszukaj</button>
                                    </div>
                                </form>
                            </div>
                            <div className={indexStyle.indexButtonDiv}>
                                <Link to={`/newentry?dictionary=${this.state.dictionary[0].dictionaryName}`} style={{width: "30%", fontSize: "10pt"}} className={indexStyle.indexButton}>Dodaj wpis</Link>
                            </div>
                        </div>
                    </div>
				</MainWrapper>
				<Footer></Footer>
			</Container>
		)
  }
}