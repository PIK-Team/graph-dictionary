import React from "react"
import * as styles from "./labeled-input.module.css"

export default class LabeledInput extends React.Component {

    label = this.props.label ? this.props.label : "Wpisz szukaną frazę"
    buttonLabel = this.props.buttonLabel ? this.props.buttonLabel : "Szukaj"

    
    state = {
        inputValue: '',
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
                <form onSubmit={this.handleSubmit}>
                    <label clssName={styles.label}>
                        {this.label}
                        <input 
                            className={styles.input}
                            type="text"
                            name="inputValue"
                            value={this.state.inputValue}
                            onChange={this.handleInputChange}
                        />
                    </label>
                    <button type="submit">{this.buttonLabel}</button>
                </form>
  )}
 
}