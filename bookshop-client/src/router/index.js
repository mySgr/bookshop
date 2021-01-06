import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from '../components/Login'
import Index from '../components/Index'
import IndexHome from '../components/main/Home'
import Classify from '../components/main/Classify'
import BookInfo from '../components/main/BookInfo'
import Home from '../components/Home'
import Welcome from '../components/Welcome'
import Users from '../components/Users'
import Member from '../components/Member'
import BookClassify from '../components/goods/BookClassifys'
import BookList from '../components/goods/BookList'
import BookAdd from '../components/goods/BookAdd'
import BookEdit from '../components/goods/BookEdit'
import OrderList from '../components/order/OrderList'
import OrderInfo from '../components/order/OrderInfo'

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        component: Login
    },
    {
        path: '/index',
        component: Index,
        redirect: '/index',
        children: [
            {path: '/index', component: IndexHome},
            {path: '/classify', component: Classify},
            {path: '/bookinfo', component: BookInfo}
        ]
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
                path: '/member',
                component: Member
            },
            {
                path: '/bookclassify',
                component: BookClassify
            },
            {
                path: '/booklist',
                component: BookList
            },
            {
                path: '/book/edit',
                component: BookEdit
            },
            {
                path: '/home/add',
                component: BookAdd
            },

            {
                path: '/home/order/list',
                component: OrderList
            },
            {
                path: '/home/order/info',
                component: OrderInfo
            },
        ],
    },


]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
