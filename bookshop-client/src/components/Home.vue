<template>
    <el-container class="home-container">
        <!-- 头部部分 -->
        <el-header>
            <el-row :gutter="20">
                <el-col :span="20">
                    <div class="grid-content bg-purple"><h3>万国书店后台管理系统</h3></div>
                </el-col>
                <el-col :span="4">
                    <div class="grid-content bg-purple">
                        <el-button class="info" @click="logout">退出</el-button>
                    </div>
                </el-col>
            </el-row>

        </el-header>
        <!-- 主要内容 -->
        <el-container>
            <!-- 侧边栏菜单 -->
            <el-aside :width="fold?'64px':'200px'">
                <!-- 侧边栏菜单区域 -->
                <el-menu :collapse-transition="false" router :collapse="fold" background-color="#545c64"
                         text-color="#fff" active-text-color="#ffd04b" unique-opened>
                    <!-- 折叠菜单 -->
                    <div class="fold" :title="title" @click="doFold">
                        <span :class="fold ? 'el-icon-d-arrow-right' : 'el-icon-d-arrow-left'"></span>
                    </div>
                    <!--el-submenu 一级菜单 -->
                    <el-submenu index="1">
                        <!-- template 一级菜单的模板区域 -->
                        <template slot="title">
                            <!-- i 图标 -->
                            <i class="el-icon-user"></i>
                            <!--span 文本 -->
                            <span>用户管理</span>
                        </template>
                        <!-- 二级菜单 -->
                        <el-menu-item index="/users">
                            <span>用户列表</span>
                        </el-menu-item>
                        <el-menu-item index="/member">
                            <span>会员列表</span>
                        </el-menu-item>
                    </el-submenu>

                    <el-submenu index="2">
                        <template slot="title">
                            <i class="el-icon-notebook-2"></i>
                            <span>书籍管理</span>
                        </template>
                        <el-menu-item index="2-1">
                            <span>书籍列表</span>
                        </el-menu-item>
                        <el-menu-item index="2-2">
                            <span>书籍分类</span>
                        </el-menu-item>
                    </el-submenu>

                    <el-submenu index="3">
                        <template slot="title">
                            <i class="el-icon-s-order"></i>
                            <span>订单管理</span>
                        </template>
                        <el-menu-item index="3-1">
                            <span>订单列表</span>
                        </el-menu-item>
                    </el-submenu>
                </el-menu>
            </el-aside>
            <!--  主体内容-->
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>

</template>

<script>
    export default {
        data() {
            return {
                title: "折叠",
                fold: false,
                active: {
                    color: "#ffd04b"
                }
            }
        },
        methods: {
            //退出登录
            logout() {
                window.sessionStorage.removeItem('user')
                this.$router.push('/login')
            },
            //折叠
            doFold() {
                this.title = this.fold ? "折叠" : "展开";
                this.fold = !this.fold;
            }
        },
        created() {
        }
    }
</script>

<style scoped="scoped">
    header {
        width: 100%;
        background-color: #545c64;
    }

    header h3 {
        color: #FFFFFF;
    }

    .fold {
        background-color: #CCCCCC;
        text-align: center;
        cursor: pointer;
    }

    .home-container {
        height: 100%;
    }

    .el-aside {
        background-color: #545c64;
        height: 100%;

    }

    .el-menu {
        border: none;
    }

    .el-main {
        background-color: #FFFFCC;
    }

    .statu {
        color: #ffd04b;
    }
</style>
