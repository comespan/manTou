<template>
  <layout>
    <uv-sticky
      bg-color="#F5F5F5"
      offset-top="0"
      customNavHeight="0"
    >
      <uv-navbar
        :fixed="false"
        :safeAreaInsetTop="true"
        height="0"
        bgColor="transparent"
        leftIcon=""
      />
      <!-- #ifndef MP -->
      <blank size="10"></blank>
      <!-- #endif -->

      <blank size="5"></blank>
    </uv-sticky>

    <blank size="10"></blank>
	<view class="container">
			<view class="banner">
				<uv-swiper class="bg" height="300" imgMode="aspectFill" keyName="image" :list="listAds" indicatorMode="dot" indicatorStyle="bottom"></uv-swiper>
				<view class="intro">
					<view class="greet">您好，{{ isLogin ? member.nickname : '游客' }}</view>
					<view class="note">java-springboot-意向点餐外卖系统</view>
				</view>
			</view>
			<view class="content">
				<view class="flex justify-between yshop-menu">
					<view class="flex flex-column align-center yshop-menu-item "  @tap="takein">
						<view><image src="/static/images/index002.png" mode="aspectFit" class="img-01"></image></view>
						<view>自取</view>
						<view class="font-small text-light-black">下单免排队</view>
					</view>
					<view class="flex flex-column align-center yshop-menu-item "  @tap="takeout">
						<view><image src="/static/images/index003.png" mode="aspectFit" class="img-01"></image></view>
						<view>外卖</view>
						<view class="font-small text-light-black">美食送到家</view>
					</view>
				</view>
				 <view class="info">
					<view class="integral_section">
						<view class="top">
							<text class="title">我的卡券</text>
							<text class="value">{{member.couponCount}}</text>
						</view>
						<view class="bottom">
							可抵扣商品价格哦
						</view>
					</view>
					<view class="qrcode_section" @tap="coupons">
						去领个券
				<!-- 		<view class="iconfont iconarrow-right"></view> -->
				
					</view>
				</view> 
				<view class="info">
					<view class="integral_section" @tap="goScore">
						<view class="top">
							<text class="title">积分商城</text>
						</view>
						<view class="bottom">
							进入积分商城兑换奈雪券及周边好礼
						<!-- 	<view class="iconfont iconarrow-right"></view> -->
						</view>
					</view>
					<view class="qrcode_section" @tap="goScore">
						<image src="/static/images/jifen.png"></image>
						<text>逛一逛</text>
					</view>
				</view>
				
			</view>
	</view>
  </layout>
</template>

<script setup>
import {
  ref
} from 'vue'
import { onLoad } from '@dcloudio/uni-app'
import {
  menuAds
} from '@/api/market'
import { storeToRefs } from 'pinia'
import { useMainStore } from '@/store/store'
//
const main = useMainStore()
const { member,store, isLogin} = storeToRefs(main)
//const store = ref(main.store)
const listAds = ref([])
// const isLogin = ref(main.isLogin)

const handGetListAds = async () => {
	let shop_id = store.id ? store.id : 0;
	let data = await menuAds({
		shop_id: shop_id
	});
	if (data) {
		listAds.value = data.list;
		console.log('listAds:',listAds.value)
		uni.setStorage({
				key: 'isActive',
				data: data.isActive
			});
		if(data.list.length > 0){
			uni.setStorage({
					key: 'shopAd',
					data: data.list[0].image
			 });
			}
		}
}

const takein = () => {
	main.SET_ORDER_TYPE('takein')
	uni.switchTab({
		url: '/pages/menu/menu'
	})
}

const takeout = () => {
	main.SET_ORDER_TYPE('takeout')
	uni.switchTab({
		url: '/pages/menu/menu'
	}) 
}

const coupons = () => { 
	console.log("--> % orderType:\n", main.orderType)
	console.log("--> % isLogin:\n", main.isLogin)
	if(!main.isLogin) {
		uni.navigateTo({url: '/pages/components/pages/login/login'})
		return
	}
	uni.navigateTo({
		url: '/pages/components/pages/coupons/coupons'
	})
}

const goScore = () => { 
	uni.navigateTo({
		url: '/pages/components/pages/scoreproduct/list'
	})
}




onLoad(() => {
 // main.init()
  handGetListAds()
})

</script>

<style lang="scss">
.yshop-menu {
	//margin-bottom: 10rpx;
	// position: relative;
	margin-top: 20rpx;
}
.yshop-menu-item {
	background-color: #ffffff;
	width: 355rpx;
	padding-bottom: 30rpx;
}
	
.img-01 {
	width: 100px;
	height: 100rpx;
	margin-top: 30rpx;
}
.img-02 {
	width: 110px;
	height: 110rpx;
	margin-top: 20rpx;
}
	
	
/* #ifdef H5 */
page {
	height: auto;
	min-height: 100%;
}
/* #endif */
page {
	//background-color: #ffffff!important;
}
.banner {
	position: relative;
	width: 100%;
	//height: 300rpx;
	
	.bg {
		width: 100%;
		height: 300rpx;
	}
	
	.intro {
		position: absolute;
		top: calc(50rpx + var(--status-bar-height));
		left: 40rpx;
		color: #FFFFFF;
		display: flex;
		flex-direction: column;
		
		.greet {
			font-size: $font-size-lg;
			margin-bottom: 10rpx;
		}
		
		.note {
			font-size: $font-size-sm;
		}
	}
}

.content {
	padding: 0 15rpx;
}


.info {
	position: relative;
	margin: 10rpx 0;
	border-radius: 10rpx;
	background-color: #ffffff;
	box-shadow: $box-shadow;
	//padding: 30rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	padding: 25rpx;
	
	.integral_section {
		flex: 1;
		display: flex;
		flex-direction: column;
		justify-content: center;
		
		.top {
			display: flex;
			align-items: center;
			
			.title {
				color: $text-color-base;
				font-size: $font-size-base;
				margin-right: 10rpx;
			}
			.value {
				font-size: 44rpx;
				font-weight: bold;
			}
		}
		
		.bottom {
			font-size: $font-size-sm;
			color: $text-color-assist;
			display: flex;
			align-items: center;
		}
	}
	
	.qrcode_section {
		color: $color-primary;
		display: flex;
		//flex-direction: column;
		align-items: center;
		justify-content: center;
		font-size: $font-size-sm;
		
		image {
			width: 40rpx;
			height: 40rpx;
			margin-bottom: 10rpx;
		}
	}
}

</style>
