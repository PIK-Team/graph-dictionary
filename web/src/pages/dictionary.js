import React from "react"
import Container from "../components/container"
import Header from '../components/header'
import SubpageHeader from '../components/subpageheader'
import Footer from '../components/footer'
import MainWrapper from '../components/mainwrapper'
import * as dictionaryStyle from '../styles/dictionary.module.css'


export default class DictionaryPage extends React.Component {

    state = {
        term: '',
        definition: '',
        parent: '',
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
        // event.preventDefault()
        // implement custom behavior
    }

    render() {
        return (
            <Container>
                <Header></Header>
                <SubpageHeader subpageName="Słownik"></SubpageHeader>
                    <MainWrapper>
                        <form onSubmit={this.handleSubmit}>
                            <label>
                                Hasło
                                <input 
                                    type="text"
                                    name="term"
                                    value={this.state.term}
                                    onChange={this.handleInputChange}
                                />
                            </label>
                            <label>
                                Definicja
                                <textarea
                                    type="text"
                                    name="definition"
                                    value={this.state.definition}
                                    onChange={this.handleInputChange}
                                />
                            </label>
                            <label>
                                Rodzic
                                <input
                                    type="text"
                                    name="parent"
                                    value={this.state.parent}
                                    onChange={this.handleInputChange}
                                />
                            </label>
                            <div className={dictionaryStyle.buttonWrapper}>
                                <button type="submit">Dodaj</button>
                            </div>
                        </form>
                    </MainWrapper>
                <Footer></Footer>
            </Container>
                
  )}
 
}