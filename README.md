# Autism Searches Machine Learning
To view other parts of the project click the links below
- [Back End](https://github.com/Software-Engineering-Final-Project/articleFetch)
- [Front End](https://github.com/Software-Engineering-Final-Project/articleRecommender)

### Brief Overview
Machine Learning on this project is a content-based recommendation system written in Python. Python was chosen because of the vast library support, and the wealth of information supporting them. A list of articles pulled from an API fetcher (written in Java) is stored in the database. A python script is then run on that data, and the recommendations for each article are returned to the backend. Currently, the articles are scored using TFIDF vector space model, and articles are recommended based on cosine similarity. Future additions to the project will include a combination of a Collaborative filtering algorithm based on user preferences along with the content-based recommendation.
