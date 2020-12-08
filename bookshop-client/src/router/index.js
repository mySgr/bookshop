import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from '../components/Login'
import Home from '../components/Home'
import Welcome from '../components/Welcome'
import Users from '../components/Users'
import Member from '../components/Member'
Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        component: Login
    },
    {
        path: '/home',
        component: Home,
        redirect: '/welcome',
        children: [
            {
                path: '/welcome',
                component: Welcome
            },
            {
                path: '/users',
                component: Users
            },
            {
                path:'/member',
                component: Member
            }

        ]
    }

]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
