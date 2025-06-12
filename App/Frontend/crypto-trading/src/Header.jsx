import { Link } from 'react-router-dom';

function Header(props) {

    let user = props.user;

    if (props.isLoggedIn) {
        return (
            <header>
                <nav>
                    <Link className="" to="/">Home</Link>
                    <Link className="" to="/Profile">Profile</Link>
                    <Link className="" to="/Holdings">Holdings</Link>
                    <Link className="" to="/Transactions">Transactions</Link>
                </nav>

                <div style={{ color: 'white', fontWeight: 'bold' }}>
                    Balance: ${user.balance}
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