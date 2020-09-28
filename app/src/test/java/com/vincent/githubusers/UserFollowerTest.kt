package com.vincent.githubusers

import org.junit.Test
import kotlin.math.ceil

/**
 * Created by Vincent on 2020/9/28.
 */
class UserFollowerTest {

    @Test
    fun followerRequestTimesTest() {
        val followers = 101
        val followingRequestTimes = ceil(followers.toDouble() / 100).toInt()

        println("FollowingRequestTimes: $followingRequestTimes")

        assert(followingRequestTimes > 1)
    }

}