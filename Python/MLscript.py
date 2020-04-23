# This code is based from: https://github.com/nikitaa30/Content-based-Recommender-System/blob/master/recommender_system.py and Mr. Cook

import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import linear_kernel

ds = pd.read_csv("C:\\Users\\sachin mahashabde\\desktop\\python\\testData.csv")

tf = TfidfVectorizer(analyzer='word', ngram_range=(1, 3), min_df=0, stop_words='english')
tfidf_matrix = tf.fit_transform(ds['description'])

cosine_similarities = linear_kernel(tfidf_matrix, tfidf_matrix)

results = {}

for idx, row in ds.iterrows():
    similar_indices = cosine_similarities[idx].argsort()[:-100:-1]
    similar_items = [(cosine_similarities[idx][i], ds['id'][i]) for i in similar_indices]

    results[row['id']] = similar_items[1:]

print('done!')


def item(id):
    return ds.loc[ds['id'] == id]['description'].tolist()[0].split(' - ')[0]


# Just reads the results out of the dictionary.
def recommend(item_id, num):
    print("-------")
    print("Recommending " + str(num) + " articles similar to " + item(item_id) + "...")
    print("-------")
    recs = results[item_id][:num]
    for rec in recs:
        print("Recommended: " + item(rec[1]) + " (score:" + str(rec[0]) + ")")


i = 1
while i < 20:
    recommend(item_id=i, num=2)
    i += 1
#recommend(item_id=3, num=3)
