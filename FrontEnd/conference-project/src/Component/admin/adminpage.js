import './adminpage.css';
import Sidebar from './components/Sidebar';
import Content from './components/Content';
import Profile from './components/Profile';

function Adminpage() {
  return (
    <div className="dashboard">
      <Sidebar/> 
      <div className='dashboard--content'>
        <Content />
        <Profile />
      </div>
    </div>
  );
}

export default Adminpage;
