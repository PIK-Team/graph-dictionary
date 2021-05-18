import React from "react"
import {Link} from "gatsby"
import Container from "../components/container"
import Header from '../components/header'
import SubpageHeader from '../components/subpageheader'
import Footer from '../components/footer'
import MainWrapper from '../components/mainwrapper'
import * as dictionaryListStyle from '../styles/dictionarylist.module.css'


export default class DictionaryList extends React.Component {
    dictionarylist =  [
        {
            name: "Słownik medyczny",
            description: "Słownik terminów medycznych.",
            logoUrl: "https://euro-bion.pl/wp-content/uploads/2020/08/AdobeStock_120965550.jpeg"
        },
        {
            name: "Słownik sportowy",
            description: "Truskawka na torcie itd.",
            logoUrl: "https://bi.im-g.pl/im/ff/72/15/z22487807IER,Truskawka-na-torcie.jpg"
        },
        {
            name: "Słownik języka włoskiego",
            description: "Parliamo italiano!",
            logoUrl: "https://assets.tmecosys.com/image/upload/t_web767x639/img/recipe/ras/Assets/658A0A74-039A-487C-A07A-CAAF61B4615D/Derivates/A230DF28-60DF-429D-ABDA-96ED64E9EE10.jpg"
        }
    ]
	
	
	render() {
		return(
			<Container>
				<Header></Header>
				<SubpageHeader subpageName="Lista słowników"></SubpageHeader>
				<MainWrapper>
                    <div className={dictionaryListStyle.list}>
                        {this.dictionarylist.map(dictionary => (
                            <Link to="#">
                                <div className={dictionaryListStyle.overlay}>
                                    <div className={dictionaryListStyle.row}>
                                        <img src={dictionary.logoUrl} alt="logo" width="120" height="120"></img>
                                        <div>{dictionary.description}</div>
                                        <div className={dictionaryListStyle.dictName}>{dictionary.name}</div>
                                    </div>
                                </div>
                            </Link>
                        ))}
                    </div>
				</MainWrapper>
				<Footer></Footer>
			</Container>
		)
  }
}