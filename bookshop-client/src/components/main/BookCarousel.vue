<template>
    <!--轮播的开始-->
    <div class="recom">
        <el-carousel @change="carouselChange" trigger="click" class="xxx" :autoplay="false" height="400px">
            <el-carousel-item class="recomWrap" v-for="item in 3" :key="item">

                <h3 class="book-name" @click="$router.push('/bookinfo?bid='+book.productNo)">
                    {{book.bookName}}
                </h3>
                <p class="content">{{book.content}}</p>

                <div>
                    <!--图片轮播-->
                    <ul>
                        <li v-for="(i,index) in bookRecom" :key="index">
                            <img @click="$router.push('/bookinfo?bid=' + i.productNo);" @mouseleave="mouseLeave"
                                 :src="'/image/'+i.cover"
                                 @mouseenter="over(i,index)"
                                 :style="n===index ? active:''">
                        </li>
                    </ul>
                </div>
            </el-carousel-item>
        </el-carousel>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                bookList: [],
                time: '',
                n: 0,
                active: {},
                book: {},
                bookRecom: [],
                image: [
                    {id: 1, cover: '/image/img/a.jpg', content: "一起学习"},
                    {id: 2, cover: '/image/img/b.jpg', content: "一起学习历史"},
                    {id: 3, cover: '/image/img/a.jpg', content: "一起学习"},
                    {id: 4, cover: '/image/img/b.jpg', content: "一起学习历史"}
                ]
            }
        },
        methods: {
            loadBookList() {
                this.$axios({
                    url: '/api/book/list'
                }).then(resp => {
                    console.log(resp)
                })
            },
            //鼠标移入时
            over(i, index) {
                this.n = index
                clearInterval(this.time);
                this.book = i;

            },
            //鼠标移出图片时，去除 width and height,并重新调用轮播
            mouseLeave() {
                clearInterval(this.time);
                console.log("移出")
                this.active = {}
                this.go()
            },
            //轮播的开始
            go() {
                this.book = this.bookRecom[this.n]
                this.active = {width: '170px', height: '220px', cursor: "pointer"}
                this.time = window.setInterval(() => {

                    this.n++
                    if (this.n == this.bookRecom.length) {
                        this.n = 0
                    }
                    this.book = this.bookRecom[this.n]


                }, 3000)
            },
            //轮播索引变化时,加载相应的数据
            carouselChange(index) {
                //sql 1=励志，2=历史，5=文学
                //轮播索引等于 0 时显示励志类型的书籍、、、
                if (index === 0) {
                    index = 1
                } else if (index === 1) {
                    index = 2
                } else if (index === 2) {
                    index = 5
                }

                this.$axios({
                    url: '/api/book/recom',
                    params: {typeNo: index}
                }).then(resp => {
                    console.log(resp)
                    if (resp.data.code === 200) {
                        clearInterval(this.time);
                        this.bookRecom = resp.data.data;
                        //调用轮播
                        this.n = 0
                        this.go()
                    } else {
                        this.$message.error(resp.data.message)
                    }
                })
            },
        },
        created() {
            this.carouselChange(0)
            this.loadBookList();
            this.$emit("active-index", "/bookinfo")
        }
    }
</script>

<style scoped>
    .recomWrap h3 {
        text-align: center;
        color: white;
    }

    .recomWrap .book-name:hover {
        color: red;
        cursor: pointer;
    }

    .recomWrap p {
        color: #eeeeee;
        font-size: 18px;
        text-align: center;
        opacity: 0.5;
    }

    .recomWrap .content {
        width: 50%;
        margin: 0 auto;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 1;
        overflow: hidden;
    }

    .bbb {
        width: 200px;

    }

    .recomWrap div ul {
        display: flex;
        list-style: none;
        justify-content: center;
    }

    .recomWrap div ul li {
        width: 177px;
        height: 230px;
        margin: 10px;
    }

    .recomWrap div img {
        width: 156px;
        height: 208px;
        transition: width 1s, height 1s; /* css3 过渡效果*/
        -webkit-transition: width 0.1s, height 0.1s;
    }

    .recomWrap div img:hover {
        cursor: pointer;
    }

    /*.yyy {*/

    /*    width: 156px;*/
    /*    height: 208px;*/
    /*    transition: width 1s, height 1s;*/
    /*    !*-webkit-transition: width 0.5s, height 0.5s; !* Safari *!*!*/

    /*}*/


    .xxx {
        background-color: #99a9bf;
    }
</style>