import path from 'path';

import HtmlWebpackPlugin from "html-webpack-plugin";
import ScriptExtHtmlWebpackPlugin from 'script-ext-html-webpack-plugin';

export default {
    entry: {
        index: path.join(__dirname, 'src/js/index.js'),
        products: path.join(__dirname, 'src/js/products.js'),
    },


    output: {
        path: path.join(__dirname, 'dist'),
        filename: './js/[name].bundle.js'
    },

    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                use: [
                    {
                        loader: 'babel-loader'
                    }
                ]
            },

            {
                test: /\.s[ac]ss$/i,
                exclude: /(node_modules)/,
                use: [
                        // Creates `style` nodes from JS strings
                        'style-loader',
                        // Translates CSS into CommonJS
                        'css-loader',
                         // Compiles Sass to CSS
                        'sass-loader',

                ]
            },
            {
                test: /\.css$/i,
                use: ['style-loader', 'css-loader'],
            },

            {
                test: /\.(png|jpg|jpeg|gif)$/,
                use: [
                    'file-loader'
                ]
            },
            {
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            filename: "index.html",
            template: path.join(__dirname, 'src/html/index.html'),
            title: 'INDEX',
            chunks: ['index'],
            hash: true
        }),
        new HtmlWebpackPlugin({
            filename: "products.html",
            template: path.join(__dirname, 'src/html/products.html'),
            title: 'PRODUCTS',
            chunks: ['products'],
            hash: true
        }),
        new ScriptExtHtmlWebpackPlugin({
            defaultAttribute: 'async'
            // defer vs async
        })
    ],

    devServer: {
        contentBase: './dist',
        inline: true,
        port: 3000
    }
}

'https://www.npmjs.com/package/extract-text-webpack-plugin'
