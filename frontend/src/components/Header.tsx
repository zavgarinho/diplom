import { NavLink } from 'react-router-dom'

const Header = () => {
  return (
    <nav className='navbar navbar-expand-lg navbar-dark bg-dark shadow-sm sticky-top'>
      <div className='container'>
        <NavLink className='navbar-brand fw-bold' to='/'>Охоронна компанія</NavLink>
         
        <ul className='navbar-nav'>
          <li className='nav-item'>
            <NavLink className='nav-link px-3' to='/'>Головна</NavLink>
          </li>
          <li className='nav-item'>
            <NavLink className='nav-link px-3' to='/customers'>Клієнти</NavLink>
          </li>
          <li className='nav-item'>
            <NavLink className='nav-link px-3' to='/objects'>Об'єкти</NavLink>
          </li>
        </ul>
      </div>
    </nav>
  )
}

export default Header