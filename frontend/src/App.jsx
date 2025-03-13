import './App.css';
import SignUpForm from './components/SignUpForm';
import LoginForm from './components/LoginForm';
import LogoutForm from './components/Logout';
import Tweet from './components/Tweet';
import GetRoles from './components/GetRoles';
import HomeFeed from './components/HomeFeed';


function App() {
  return (
    <div>
      
      <GetRoles/>
      <HomeFeed/>
      <SignUpForm />
      <LoginForm />
      <LogoutForm/>

      <Tweet/>


    </div>
  );
}

export default App;
