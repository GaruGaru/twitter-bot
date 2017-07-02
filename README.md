## Twitter bot framework written in java based on Twitterj4 


## Usage 

### Create twitterj4.properties file under main/resources folder

    oauth.consumerKey=***********************
    oauth.consumerSecret=***********************
    oauth.accessToken=***********************
    oauth.accessTokenSecret=***********************

###  Pendulum bot (please just don't do this)

        TwitterBot bot = TwitterBot.builder()
                .twitter(TwitterApi.fromTwitter(TwitterFactory.getSingleton()))
                .task(twitter -> twitter.twit("tick"), 1, TimeUnit.SECONDS)
                .task(twitter -> twitter.twit("Donn"), 1, TimeUnit.HOURS)
                .build();
        bot.run();


### Rss example 

            TwitterBot bot = TwitterBot.builder()
                    .twitter(TwitterApi.fromTwitter(TwitterFactory.getSingleton()))
                    .task(RssTask.create("https://www.reddit.com/r/programming/.rss"), 6, TimeUnit.HOURS)
                    .build();
    
            bot.run();