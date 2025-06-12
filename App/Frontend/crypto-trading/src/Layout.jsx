import React from 'react';
import { Outlet } from 'react-router-dom';
import Header from './Header';

export default function Layout({ isLoggedIn, user }) {
  return (
    <>
      <Header isLoggedIn={isLoggedIn} user={user}  />
      <Outlet />
    </>
  );
}