const functions = require('firebase-functions');
const admin = require('firebase-admin');

admin.initializeApp()
const database = admin.database()


exports.caculatRating = functions.database.ref('Place/{placeId}/comments/{commentId}/rating')
    .onCreate((snapshot, context) => {
        const rating = snapshot.val();

        const place = database.ref('Place').child(context.params.placeId)

        place.once("value", function (psnap) {
            const placedata = psnap.val()

            let countrating = placedata.countRating || 0
            let sumrating = placedata.sumRating || 0

            countrating += 1;
            sumrating += rating;

            const averageRating = sumrating / countrating;


            place.update({
                averageRating: averageRating,
                countRating: countrating,
                sumRating: sumrating
            })

        })
    })

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
