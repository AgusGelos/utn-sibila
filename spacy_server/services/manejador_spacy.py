import spacy


class ManejadorSpacy:

    @staticmethod
    def analizar_texto(texto):

        nlp = spacy.load('es_core_news_sm')
        tokens = nlp(texto)

        tokens_dic = []

        for token in tokens:
            print(token)
            token_dic = {
                "text": token.text,
                "index": token.idx,
                "raiz": token.lemma_,
                "id_punct": token.is_punct,
                "is_space" : token.is_space,
                "shape": token.shape_,
                "type": token.pos_,
                "norm": token.norm_,
                "tags": token.tag_,
                "dep": token.dep_,
                "prob": token.prob,
                "sentiment": token.sentiment,
                "entity": token.ent_type_
            }

            tokens_dic.append(token_dic)

        return tokens_dic


