package com.example.blogapp.ui.components

import android.service.autofill.OnClickAction
import android.view.View.OnClickListener
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ModeComment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.blogapp.R
import com.example.blogapp.core.DateFormatter
import com.example.blogapp.data.model.Post
import com.example.blogapp.data.model.Poster


@Composable
fun PostItemComponent(
    post: Post,
    modifier: Modifier = Modifier
) {

    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceContainerHighest,
        modifier = modifier.padding(bottom = 30.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            PostProfileInfo(post = post)
            PostImage(postImage = post.postImage)
            PostLikesAndCommentsCount(post = post)
        }
    }

}

@Composable
fun PostProfileInfo(
    modifier: Modifier = Modifier,
    post: Post
) {

    post.poster?.let {

        Surface(
            modifier = modifier.fillMaxWidth(),
            color = Color.Transparent
        ) {

            Row(
                modifier = Modifier
                    .width(255.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it.profilePicture)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )

                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    it.profileName?.let {
                        Text(
                            text = it,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

//                Text(
//                    text = location,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    style = MaterialTheme.typography.bodySmall)
                }

                val timeStamp = post.createdAt?.let {
                    DateFormatter.getTimeAgo(it)
                }

                timeStamp?.let {

                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

    }
}


@Composable
fun PostProfileInfoTest(
    modifier: Modifier = Modifier,
    profilePicture: Int,
    profileName: String,
    location: String,
    postTime: String
) {

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {

        Row(
            modifier = Modifier
                .width(255.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Image(
                painter = painterResource(id = profilePicture),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = profileName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = location,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall
                )
            }


            Text(
                text = postTime,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun PostImage(modifier: Modifier = Modifier, postImage: String) {


    Surface(
        shape = MaterialTheme.shapes.large,
        shadowElevation = 10.dp,
        modifier = modifier
            .padding(vertical = 8.dp)
            .padding(start = 8.dp, end = 8.dp)
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(postImage)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )
    }

}

@Composable
fun PostImageTest(modifier: Modifier = Modifier, postImage: Int) {

    Surface(
        shape = MaterialTheme.shapes.large,
        shadowElevation = 10.dp,
        modifier = modifier
            .padding(vertical = 10.dp)
    ) {
        Image(
            painter = painterResource(id = postImage),
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )
    }

}


@Composable
fun PostLikesAndCommentsCount(modifier: Modifier = Modifier, post: Post) {

    val likesCount = post.likes
    //val commentsCount

    val isPostLiked by remember {
        mutableStateOf(post.liked)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {


        Icon(
            imageVector = Icons.Filled.Favorite,
            tint = if(isPostLiked) Color.Red else Color.Black,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = likesCount.toString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(25.dp))

        Icon(
            imageVector = Icons.Filled.ModeComment,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(4.dp))

//        Text(
//            text = commentsCount.toString(),
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis
//        )
    }
}

@Composable
fun PostLikesRowTest(
    posters: List<PosterLocalTest>
) {

    if (posters.isNotEmpty()) {

        var subList = posters.take(3)

        var postersNames = emptyList<String?>()
        var postersPictures = emptyList<Painter>()

        subList.map {
            postersNames += it.profileName
            postersPictures += it.profilePicture
        }

        val displayNames = if (postersNames.size > 1) {
            postersNames.joinToString(", ") + "..."
        } else {
            postersNames.get(0)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 10.dp)
        ) {

            val offset = 20
            Box {

                val size = subList.size - 1

                postersPictures.asReversed().forEachIndexed { index, picture ->

                    Image(
                        painter = picture,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .offset(x = (size - index).dp * offset)
                            .border(2.dp, Color.White, CircleShape)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop

                    )

                }

            }


            val spaceDp = ((subList.size - 1) * offset) + 4
            Spacer(modifier = Modifier.width((spaceDp).dp))

            if (displayNames != null) {
                Text(
                    text = displayNames,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

    }

}

@Composable
fun PostLikesRow(
    posters: List<Poster>
) {

    if (posters.isNotEmpty()) {

        var subList = posters.take(3)

        var postersNames = emptyList<String?>()
        var postersPictures = emptyList<String?>()

        subList.map {
            postersNames += it.profileName
            postersPictures += it.profilePicture
        }

        val displayNames = if (postersNames.size > 1) {
            postersNames.joinToString(", ") + "..."
        } else {
            postersNames.get(0)
        }


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            val offset = 20
            Box {

                val size = subList.size - 1

                postersPictures.asReversed().forEachIndexed { index, picture ->


                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(picture)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .offset(x = (size - index).dp * offset)
                            .border(2.dp, Color.White, CircleShape)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                }

            }


            val spaceDp = ((subList.size - 1) * offset) + 4
            Spacer(modifier = Modifier.width((spaceDp).dp))

            if (displayNames != null) {
                Text(
                    text = displayNames,
                    style = MaterialTheme.typography.bodySmall
                )
            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun PostItemComponentPreview() {

    val users = listOf(
        PosterLocalTest("Sasha", painterResource(id = R.drawable.golden)),
        PosterLocalTest("Victor", painterResource(id = R.drawable.instagram_logo)),
        PosterLocalTest("Natalia", painterResource(id = R.drawable.golden)),
        PosterLocalTest("Camila", painterResource(id = R.drawable.instagram_logo)),
        PosterLocalTest("Laura", painterResource(id = R.drawable.golden)),
        PosterLocalTest("Pedro", painterResource(id = R.drawable.instagram_logo)),

        )

    val users2 = emptyList<PosterLocalTest>()

    val post = Post(likes = 10)

    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerHighest
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            PostProfileInfoTest(
                profilePicture = R.drawable.golden,
                profileName = "Sasha",
                location = "Bogotá, Colombia",
                postTime = "2 hours ago"
            )

            PostImageTest(postImage = R.drawable.golden)

            PostLikesAndCommentsCount(post = post)
            //Spacer(modifier = Modifier.height(10.dp))

            PostLikesRowTest(posters = users)
        }

    }

}

data class PosterLocalTest(
    val profileName: String,
    val profilePicture: Painter
)
