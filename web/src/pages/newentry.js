import React from "react"
import Container from "../components/container"
import Header from '../components/header'
import queryString from "query-string"
import SubpageHeader from '../components/subpageheader'
import Footer from '../components/footer'
import MainWrapper from '../components/mainwrapper'
import * as formStyle from '../styles/forms.module.css'
import * as newDicStyle from '../styles/newdic.module.css'


export default class NewEntry extends React.Component {

    values = queryString.parse(this.props.location.search)
	
    state = {
        word: '',
        dictionary: this.values.dictionary,
        parent_entry: this.values.parent,
        definition: '',
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
		event.preventDefault();
 
		let addedEntry = document.getElementById("AddedEntry");
		addedEntry.style.display="none";
		
		let ErrorAddedEntry = document.getElementById("ErrorAddedEntry");
		ErrorAddedEntry.style.display="none";
		
		let AddingEntry = document.getElementById("AddingEntry");
		AddingEntry.style.display="none";
				
		if (this.state.word != "")
		{
			AddingEntry.style.display="block";
			fetch(process.env.API_URL+"entries/define", {
				method: "POST",
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify(this.state)
			})
			.then(function(response) {
				if (!response.ok)
				{
					throw Error(response.statusText);
				}
				return response;
			})
			.then(() => {	
				AddingEntry.style.display="none";
				addedEntry.style.display="block";
			})
			.catch(error => {
				console.log(error);
				AddingEntry.style.display="none";
				ErrorAddedEntry.style.display="block";
			});
		}
		
	}

    render() {
        return (
            <Container>
                <Header></Header>
                <SubpageHeader subpageName="Dodawanie nowego wpisu"></SubpageHeader>
                <MainWrapper>
                    <div id="AddingEntry" className={ `${newDicStyle.responseStyle} ${newDicStyle.addingDic}` }>Trwa dodawanie wpisu</div>
					<div id="AddedEntry" className={ `${newDicStyle.responseStyle} ${newDicStyle.addedDic}` }>Pomyślnie dodano nowy wpis do słownika</div>
					<div id="ErrorAddedEntry" className={ `${newDicStyle.responseStyle} ${newDicStyle.errorAddedDic}` }>Nie udało się dodać wpisu. Spróbuj ponownie</div>

                    <form className={`form-horizontal ${formStyle.forms}`} onSubmit={this.handleSubmit}>
                        <div className={`form-group ${formStyle.groupForms}`}>
                        <label className={`control-label ${formStyle.labelForms}`}>Hasło:</label>
                            <input className={`form-control $formStyle.textInputForms}`} 
                                type="text"
                                name="word"
                                value={this.state.word}
                                onChange={this.handleInputChange}/>
                        </div>
                        <div className={`form-group ${formStyle.groupForms}`}>
							<label className={`control-label ${formStyle.labelForms}`}>Definicja: </label>
                            <textarea className={`form-control $formStyle.textInputForms}`} 
                                type="text"
                                name="definition"
                                value={this.state.definition}
                                onChange={this.handleInputChange}/>
                        </div>
                        <div className={`form-group ${formStyle.groupForms}`}>
							<label className={`control-label ${formStyle.labelForms}`}> Wpis nadrzędny:</label>
							<input className={`form-control $formStyle.textInputForms}`}
                                type="text"
                                name="parent_entry"
                                value={this.state.parent_entry}
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