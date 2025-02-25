angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189';

    $scope.fillTable = function () {
        $http.get(contextPath + '/book')
            .then(function (response) {
                $scope.BooksList = response.data;
            });
    };

    $scope.submitCreateNewBook = function () {
        $http.post(contextPath + '/book', $scope.newBook)
            .then(function (response) {
                $scope.fillTable();
            });
    };

    $scope.deleteBookById = function(bookId) {
        $http({
            url: contextPath + '/book?id=' + bookId,
            method: "DELETE"
        }).then(function (response) {
            $scope.fillTable();
        });
    }

    $scope.updateBookById = function(bookId) {
    $http.put(contextPath + '/book', $scope.updateBook)
        $http.get(contextPath + '/book?id=' + bookId)
            .then(function (response) {
                $scope.updateBook = response.data;
            });

    }

    $scope.saveBookById = function(bookId) {
    $http.put(contextPath + '/book?id='+bookId, $scope.updateBook)
        .then(function (response) {
                $scope.updateBook = response.data;
                $scope.fillTable();
                });
    }

    // $scope.tryToGetFile = function () {
    //     $http({
    //         url: contextPath + '/123.png',
    //         cache: true,
    //         method: "GET"
    //     }).then(function (response) {
    //         console.log('ok')
    //     });
    // };

    $scope.fillTable();

    // $scope.tryToGetFile();
    // $scope.tryToGetFile();
    // $scope.tryToGetFile();
});