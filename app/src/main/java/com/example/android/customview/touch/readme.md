
parent控制child :
parent的onInterceptTouchEvent返回true，给自己的onTouchEvent消费，通过child.dispatchTouchEvent(event)继续传给child
见demo1/TouchForwardLayout

child控制parent :
child在dispatchTouchEvent()里，通过requestDisallowInterceptTouchEvent(false)控制parent