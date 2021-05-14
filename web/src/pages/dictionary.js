import React from "react"
import Container from "../components/container/container"
import LabeledInput from "../components/labeled-input/labeled-input"


export default class DictionaryPage extends React.Component {
    state = {
        dictionaryName: '',
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
        // alert(`Welcome ${this.state.dictionaryName}`)
    }

    render() {
        return (
            <Container>

                <LabeledInput label='Wyszukaj słownik' buttonLabel="Szukaj"></LabeledInput>

            </Container>
  )}
 
}