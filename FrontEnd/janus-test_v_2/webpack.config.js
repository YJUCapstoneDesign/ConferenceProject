const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const webpack = require('webpack');
const Dotenv = require('dotenv-webpack');

module.exports = {
  name: 'janus-test',
  mode: 'development',
  devtool: 'eval',

  module: {
    rules: [
      {
        test: /\.jsx?/,
        exclude: /node_modules|\.json$/,  // Exclude JSON files here
        loader: 'babel-loader',
        options: {
          presets: ['@babel/preset-env', '@babel/preset-react']
        }
      },
      { 
        test: /.(png|jpg|jpeg)$/,
        type: 'asset/resource',

      },
      {
        test: /\.svg$/,
        use: ['file-loader'],
      },
      {
        test: /\.css$/,
        use: ["style-loader", "css-loader", "postcss-loader"],
      },
      {
        test: require.resolve('janus-gateway'),
        loader: 'exports-loader',
        options: {
          exports: 'Janus',
        },
      },
    ],
  },
  entry: {
    app: ['./src/client.jsx', './src/app.css'],
  },
  output: {
    filename: 'app.js',
    path: path.resolve(__dirname, 'dist'),
    publicPath: '/',
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './index.html'
    }),
    // new MiniCssExtractPlugin({
    //   filename: 'app.css'
    // }),
    new webpack.ProvidePlugin({
      adapter: ['webrtc-adapter', 'default']
    }),
    new Dotenv(),
  ],
  devServer: {
    server: {
      
    },
    static: {
      directory: path.resolve(__dirname, 'dist')
    },
    proxy: [
        { 
          context: ['/api'],
          target: 'http://localhost:8080/api',
          changeOrigin: true,
          secure: false,
          ws: true,
          pathRewrite: { '^/api': '' }
        }
    ],
    port: 3030,
    historyApiFallback: true,
  },
  resolve: {
    extensions: [".jsx", ".js"],
  },
};