function Header(props){

    return(
        <header>
            <nav>
                <a href="#home">Home</a>
                <a href="#holdings">Holdings</a>
                <a href="#transactions">Transactions</a>
            </nav>

           {props.isLoggedIn && (
                <div style={{ color: 'white', fontWeight: 'bold' }}>
                    Balance: ${props.balance}
                </div>
            )}

        </header>
    );

}

export default Header