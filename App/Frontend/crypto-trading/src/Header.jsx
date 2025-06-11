function Header(props) {

    if (props.isLoggedIn) {
        return (
            <header>
                <nav>
                    <a href="#home">Home</a>
                    <a href="#holdings">Holdings</a>
                    <a href="#transactions">Transactions</a>
                </nav>

                <div style={{ color: 'white', fontWeight: 'bold' }}>
                    Balance: ${props.balance}
                </div>

            </header>
        )
    }

    return (
        <header>
            <nav>
                <a href="/">Home</a>
            </nav>

            <div>
                <a className="auth" href="/login">Login</a>
                <a className="auth" href="#register">Register</a>
            </div>

        </header>
    )
}

export default Header