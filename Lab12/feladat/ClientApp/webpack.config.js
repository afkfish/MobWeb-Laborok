var path = require("path");
var webpack = require("webpack");
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    mode: 'development',
    devServer: {
        static: './dist',
    },
    entry: {
        guessgame: [
            './src/client-start.js',
            'bootstrap/dist/css/bootstrap.css',
            './src/styles.css'
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            title: 'Guess Game',
            template: './src/index.html',
            output: './dist/index.html',
            inject: true,
            
        }),
        new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery"
        })
    ],
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: '[name].js',
        publicPath: '/'
    },
    module: {
        rules: [
            { test: /\.js/, include: path.resolve(__dirname, 'src'), use: { loader: 'babel-loader', options: { presets: ["@babel/preset-env"] } } },
            { test: /\.css$/, use: ['style-loader', 'css-loader'] },
            { test: /\.(png|jpg|jpeg|gif|woff|woff2|eot|ttf|svg)$/, use: 'url-loader?limit=25000' }
        ]
    }
};