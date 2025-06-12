import { Link } from 'react-router-dom';

function Header(props) {

    if (props.isLoggedIn) {
        return (
            <header>
                <nav>
                    <Link className="" to="/">Home</Link>
                    <Link className="" to="/Holdings">Holdings</Link>
                    <Link className="" to="/Transactions">Transactions</Link>
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
                <Link className="" to="/">Home</Link>
            </nav>

            <div>
                <Link className="auth" to="/login">Login</Link>
                <Link className="auth" to="/register">Register</Link>
            </div>

        </header>

    )
}

export default Header