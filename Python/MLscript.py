# This code is based from: https://github.com/nikitaa30/Content-based-Recommender-System/blob/master/recommender_system.py and Mr. Cook

import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import linear_kernel

ds = pd.read_csv("C:\\Users\\Sachin Mahashabde\\Documents\\new project\\BackEndML\\Python\\testData.csv")

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


f = open("returnResults.csv", "w")


# Just reads the results out of the dictionary.

def recommend(item_id, num):
    # print("-------")
    f.write("\n")
    f.write(str(item_id) + ", ")
    # print("-------")
    recs = results[item_id][:num]

    i = 0
    for rec in recs:
        op = "," if i < 2 else ""
        f.write(str(rec[1]) + op)
        print(i)
        i = i + 1 if i < 2 else 0


i = 1
while i < 465:
    (recommend(item_id=i, num=3))
    i += 1
f.close()
# recommend(item_id=3, num=3)
