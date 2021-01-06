FROM python:3

ENV PYTHONUNBUFFERED 1
WORKDIR /usr/src/app

COPY requirements.txt ./
RUN pip3 install --no-cache-dir -r requirements.txt
RUN mkdir review_texts
RUN mkdir /etc/scrapyd/ \
    && echo "[scrapyd] \n bind_address = 0.0.0.0" > /etc/scrapyd/scrapyd.conf

COPY ./scrapyd.conf /etc/scrapyd/
COPY . .

CMD [ "scrapyd", "--pidfile=" ]